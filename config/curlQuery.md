curl http://127.0.0.1:8080/topjava/rest/meals/100007
curl http://127.0.0.1:8080/topjava/rest/meals 
curl -d '{"dateTime":"2020-04-27T20:00:00","description": "newMeal","calories": 510}' -H 'Content-Type: application/json' http://localhost:8080/topjava/rest/meals
curl -X DELETE http://127.0.0.1:8080/topjava/rest/meals/100008
curl -d '{"dateTime":"2020-01-31T13:00:00","description": "updatedMeal","calories": 1100}' -H 'Content-Type: application/json' -X PUT http://localhost:8080/topjava/rest/meals/100007
curl http://127.0.0.1:8080/topjava/rest/meals/filter?endDate=2020-01-30&startTime=11:00:00.000-05:00&endTime=23:30:00.000-05:00&startDate=2020-01-30