package io.github.sskorol.framework.utils;

import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.extensions.java6.auth.oauth2.AuthorizationCodeInstalledApp;
import com.google.api.client.extensions.jetty.auth.oauth2.LocalServerReceiver;
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeFlow;
import com.google.api.client.googleapis.auth.oauth2.GoogleClientSecrets;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.util.store.FileDataStoreFactory;

import com.google.api.services.gmail.GmailScopes;
import com.google.api.services.gmail.model.*;
import com.google.api.services.gmail.Gmail;
import io.vavr.control.Try;
import lombok.Cleanup;
import one.util.streamex.StreamEx;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

import static com.google.api.client.googleapis.auth.oauth2.GoogleClientSecrets.load;
import static io.github.sskorol.framework.core.BaseConfig.BASE_CONFIG;
import static java.util.Collections.singletonList;

public final class EmailUtils {

    private static final String APPLICATION_NAME = "Facebook sample";
    private static final JsonFactory JSON_FACTORY = JacksonFactory.getDefaultInstance();
    private static final Gmail SERVICE;
    private static final Credential CREDENTIALS;
    private static FileDataStoreFactory DATA_STORE_FACTORY;
    private static HttpTransport HTTP_TRANSPORT;

    static {
        try {
            HTTP_TRANSPORT = GoogleNetHttpTransport.newTrustedTransport();
            DATA_STORE_FACTORY = new FileDataStoreFactory(new File("src/test/resources"));
            CREDENTIALS = authorize();
            SERVICE = new Gmail.Builder(HTTP_TRANSPORT, JSON_FACTORY, CREDENTIALS)
                    .setApplicationName(APPLICATION_NAME)
                    .build();
        } catch (Exception ex) {
            throw new IllegalStateException("Unable to prepare GMail service", ex);
        }
    }

    private EmailUtils() {
        throw new UnsupportedOperationException("Illegal access to private constructor");
    }

    public static StreamEx<Message> getMessages(final String query) throws IOException {
        ListMessagesResponse response = SERVICE.users()
                                               .messages()
                                               .list(BASE_CONFIG.gmailUserId())
                                               .setQ(query)
                                               .execute();

        final List<Message> messages = new ArrayList<>();
        while (response.getMessages() != null) {
            messages.addAll(response.getMessages());
            if (response.getNextPageToken() != null) {
                response = SERVICE.users()
                                  .messages()
                                  .list(BASE_CONFIG.gmailUserId())
                                  .setQ(query)
                                  .setPageToken(response.getNextPageToken())
                                  .execute();
            } else {
                break;
            }
        }

        return StreamEx.of(messages).map(m -> Try.of(() -> getMessage(m.getId()))
                                                 .getOrElseGet(ex -> m));
    }

    public static Message getMessage(final String messageId) throws IOException {
        return SERVICE.users().messages().get(BASE_CONFIG.gmailUserId(), messageId).setFormat("full").execute();
    }

    private static Credential authorize() throws IOException {
        @Cleanup InputStream in = EmailUtils.class.getResourceAsStream("/client_secret.json");
        final GoogleClientSecrets clientSecrets = load(JSON_FACTORY, new InputStreamReader(in));
        final GoogleAuthorizationCodeFlow flow = new GoogleAuthorizationCodeFlow.Builder(
                HTTP_TRANSPORT, JSON_FACTORY, clientSecrets, singletonList(GmailScopes.MAIL_GOOGLE_COM))
                .setDataStoreFactory(DATA_STORE_FACTORY)
                .setAccessType("offline")
                .build();
        return new AuthorizationCodeInstalledApp(flow, new LocalServerReceiver()).authorize("me");
    }
}