package eu.maxkim.boredombuster.activity

import eu.maxkim.boredombuster.activity.model.Activity

val activity1 = Activity(
    name = "Learn to dance",
    type = Activity.Type.Recreational,
    participantCount = 2,
    price = 0.1f,
    accessibility = 0.2f,
    key = "112233",
    link = "www.dance.com"
)

val activity2 = Activity(
    name = "Pet a dog",
    type = Activity.Type.Relaxation,
    participantCount = 1,
    price = 0.0f,
    accessibility = 0.1f,
    key = "223344",
    link = "www.dog.com"
)

val responseActivity = Activity(
    name = "Go to a music festival with some friends",
    type = Activity.Type.Social,
    participantCount = 4,
    price = 0.4f,
    accessibility = 0.2f,
    key = "6482790",
    link = ""
)