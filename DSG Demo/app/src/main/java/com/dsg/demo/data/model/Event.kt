package com.dsg.demo.data.model

data class Event(
    val title: String,
    val datetime_utc: String,
    val performers: List<Performer>,
    val venue: Venue
)