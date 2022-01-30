package org.craftedsw.tripservicekata.trip;

import org.craftedsw.tripservicekata.exception.UserNotLoggedInException;
import org.craftedsw.tripservicekata.user.User;
import org.craftedsw.tripservicekata.user.UserSession;

import java.util.ArrayList;
import java.util.List;

public class TripService {

    public List<Trip> getTripsByUser(User user) throws UserNotLoggedInException {
        List<Trip> tripList = new ArrayList<Trip>();
        User loggedUser = getLoggedUser();
        if (loggedUser != null) {
            boolean isFriend = isFriend(user, loggedUser);
            if (isFriend) {
                tripList = findTripsByUser(user);
            }
            return tripList;
        } else {
            throw new UserNotLoggedInException();
        }
    }

    private boolean isFriend(User user, User potentialFriendUser) {
        boolean isFriend = false;
        for (User friend : user.getFriends()) {
            if (friend.equals(potentialFriendUser)) {
                isFriend = true;
                break;
            }
        }
        return isFriend;
    }

    User getLoggedUser() {
        return UserSession.getInstance().getLoggedUser();
    }

    List<Trip> findTripsByUser(User user) {
        return TripDAO.findTripsByUser(user);
    }

}
