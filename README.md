# Sample app for interview

Use the following command for building a project:

```bash
gradlew clean test -PbuildProfile=profileName
```
Where **profileName** is either **rest** or **ui**.

Use the following command for Allure report generation:
```bash
gradlew allureReport
gradlew allureServe
```