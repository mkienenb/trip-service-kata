package org.craftedsw.tripservicekata.trip;

import org.craftedsw.tripservicekata.exception.UserNotLoggedInException;
import org.craftedsw.tripservicekata.user.User;
import org.craftedsw.tripservicekata.user.UserSession;
import org.craftedsw.tripservicekata.user.UserSessionProvider;

import java.util.ArrayList;
import java.util.List;

public class TripService {

    private final TripFinder tripFinder = new TripFinder();

    public List<Trip> getTripsByUser(User user) throws UserNotLoggedInException {
        List<Trip> tripList = new ArrayList<Trip>();
        User loggedUser = getUserSessionProvider().getLoggedUser();
        boolean isFriend = false;
        if (loggedUser != null) {
            for (User friend : user.getFriends()) {
                if (friend.equals(loggedUser)) {
                    isFriend = true;
                    break;
                }
            }
            if (isFriend) {
                tripList = tripFinder.findTripByUser(user);
            }
            return tripList;
        } else {
            throw new UserNotLoggedInException();
        }
    }

    private List<Trip> findTripByUser(User user) {
        return tripFinder.findTripByUser(user);
    }

    UserSessionProvider getUserSessionProvider() {
		return UserSession.getInstance();
	}

}
