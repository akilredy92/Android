package com.dsg.demo.data.model

/**
 * Created by Nirav Bhavsar on 4/24/2021.
 */
data class Event(
    val title: String,
    val datetime_utc: String,
    val performers: List<Performer>,
    val venue: Venue
)