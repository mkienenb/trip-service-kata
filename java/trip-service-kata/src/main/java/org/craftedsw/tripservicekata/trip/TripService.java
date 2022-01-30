package org.craftedsw.tripservicekata.trip;

import org.craftedsw.tripservicekata.exception.UserNotLoggedInException;
import org.craftedsw.tripservicekata.user.User;
import org.craftedsw.tripservicekata.user.UserSession;

import java.util.ArrayList;
import java.util.List;

public class TripService {

    public List<Trip> getTripsByUser(User user) throws UserNotLoggedInException {
        User loggedUser = getLoggedUser();
        if (loggedUser == null) {
            throw new UserNotLoggedInException();
        } else {
            List<Trip> tripList = new ArrayList<Trip>();
            if (user.isFriend(loggedUser)) {
                tripList = findTripsByUser(user);
            }
            return tripList;
        }
    }

    User getLoggedUser() {
        return UserSession.getInstance().getLoggedUser();
    }

    List<Trip> findTripsByUser(User user) {
        return TripDAO.findTripsByUser(user);
    }

}
