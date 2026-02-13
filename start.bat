@echo off
REM ========================================
REM FrontOffice - Script de démarrage
REM ========================================

setlocal enabledelayedexpansion

echo.
echo ====================================
echo FrontOffice - Application Launcher
echo ====================================
echo.

REM Vérifier Java
java -version >nul 2>&1
if errorlevel 1 (
    echo ERROR: Java not found. Please install Java 17 LTS or higher.
    pause
    exit /b 1
)

echo [OK] Java found

REM Vérifier Maven
mvn -version >nul 2>&1
if errorlevel 1 (
    echo ERROR: Maven not found. Please install Maven 3.8.1 or higher.
    pause
    exit /b 1
)

echo [OK] Maven found

REM Menu
echo.
echo Select startup mode:
echo   1) Development (localhost:8080)
echo   2) Staging (staging configuration)
echo   3) Compile only
echo   4) Clean build
echo.
set /p choice="Enter your choice (1-4): "

cd /d "%~dp0reservationVoiture\reservationVoiture"

if "%choice%"=="1" (
    echo.
    echo Starting FrontOffice in DEVELOPMENT mode...
    echo.
    mvn spring-boot:run
) else if "%choice%"=="2" (
    echo.
    echo Starting FrontOffice in STAGING mode...
    echo.
    mvn spring-boot:run -Dspring-boot.run.profiles=staging
) else if "%choice%"=="3" (
    echo.
    echo Compiling project...
    echo.
    mvn clean compile
    echo.
    echo Compilation complete!
    pause
) else if "%choice%"=="4" (
    echo.
    echo Cleaning and rebuilding...
    echo.
    mvn clean install
    echo.
    echo Build complete!
    pause
) else (
    echo Invalid choice!
    pause
    exit /b 1
)

pause
