#!/bin/bash

#ferma tutti i contenitori coinvolti nei test di integrazione
gradle :OrderService:composeDownForced
 
