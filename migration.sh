#!/bin/bash

echo 'Running.'

cat migration.sql | docker exec -i mysql-niiar mysql niiar

echo 'Done.'
