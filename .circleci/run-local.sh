#!/usr/bin/env bash

## Optionally commit hash could be added
## --form revision=3fd40af037730db3e2288c2d8afbec9511cac325 \

curl -v \
--user ${CIRCLE_TOKEN}: \
--request POST \
--form config=@config.yml \
--form notify=false \
https://circleci.com/api/v1.1/project/github/h8/budget-manager/tree/develop
