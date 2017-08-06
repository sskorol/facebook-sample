package io.github.sskorol.framework.core.listeners;

import org.testng.IInvokedMethod;
import org.testng.IInvokedMethodListener;
import org.testng.ITestResult;

public class BeforeMethodListener extends BaseListener implements IInvokedMethodListener {

    @Override
    public void beforeInvocation(final IInvokedMethod method, final ITestResult testResult) {
        if (method.isTestMethod()) {
            setupDriver();
        }
    }

    @Override
    public void afterInvocation(final IInvokedMethod method, final ITestResult testResult) {
        if (method.isTestMethod()) {
            cleanUp();
        }
    }
}
