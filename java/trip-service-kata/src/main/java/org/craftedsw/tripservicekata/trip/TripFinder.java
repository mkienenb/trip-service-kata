package org.craftedsw.tripservicekata.trip;

import org.craftedsw.tripservicekata.user.User;

import java.util.List;

public class TripFinder {
    List<Trip> findTripByUser(User user) {
        return TripDAO.findTripsByUser(user);
    }
}
