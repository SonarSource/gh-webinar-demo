#!/bin/bash

if [ "${TRAVIS_BRANCH}" == "master" ] && [ "$TRAVIS_PULL_REQUEST" == "false" ]; then
  echo '======= Analyze only master'
  mvn clean verify sonar:sonar -Pcoverage \
    -Dsonar.host.url=https://next.sonarqube.com/sonarqube/
elif [ "$TRAVIS_PULL_REQUEST" != "false" ]; then
  echo '======= Analyze pull request'
  mvn clean verify sonar:sonar -Pcoverage \
    -Dsonar.host.url=https://next.sonarqube.com/sonarqube/ \
    -Dsonar.pullrequest.branch=$TRAVIS_PULL_REQUEST_BRANCH \
    -Dsonar.pullrequest.base=$TRAVIS_BRANCH \
    -Dsonar.pullrequest.key=$TRAVIS_PULL_REQUEST \
    -Dsonar.pullrequest.github.repository=$TRAVIS_REPO_SLUG
fi



