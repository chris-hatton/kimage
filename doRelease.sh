#!/bin/bash
git checkout release
git merge master
git push origin
git checkout master
