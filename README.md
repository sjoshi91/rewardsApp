The RewardApp is created using Spring Rest and Spring Data JPA which uses MySql as database, in this app customer will get the awards on monthly basis, here we developed two api's which shows the response on monthly basis as well as total rewrds

The JSON Request and Response for API are given as

1)URL : http://localhost:9091/api/rewards/monthly/2

here 2 is the user id

JSON Response:
{
    "JANUARY": 0,
    "JUNE": 0,
    "MAY": 250,
    "OCTOBER": 0,
    "DECEMBER": 0,
    "MARCH": 0,
    "FEBRUARY": 0,
    "AUGUST": 0,
    "JULY": 0,
    "SEPTEMBER": 0,
    "NOVEMBER": 0,
    "APRIL": 0
}

2)URL: http://localhost:9091/api/rewards/total/2

JSON Response:
250

