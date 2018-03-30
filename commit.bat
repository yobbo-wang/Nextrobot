@echo off;
rem commit git
git add ./src/*
git add ./pom.xml
git add ./commit.bat
git add ./*.zip
git add ./README.md
git remote add origin https://github.com/yobbo-wang/Nextrobot.git
git commit -m 'update'
git push origin -u master