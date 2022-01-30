package org.craftedsw.tripservicekata.trip;

import org.craftedsw.tripservicekata.exception.UserNotLoggedInException;
import org.craftedsw.tripservicekata.user.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@DisplayName("A trip service")
public class TripServiceTest {
    @Nested
    @DisplayName("when called with a specific user having three trips")
    class WhenCalledWithASpecificUserHavingThreeTrips {
        final User specifiedUser = mock(User.class);
        final User sessionUser = mock(User.class);
        final Trip trip1 = mock(Trip.class);
        final Trip trip2 = mock(Trip.class);
        final Trip trip3 = mock(Trip.class);
        final List<Trip> tripListForSpecifiedUser = Stream.of(trip1, trip2, trip3).collect(Collectors.toList());

        User mockedSessionUser;

        final TripService tripService = new TripService() {
            @Override
            User getLoggedUser() {
                return mockedSessionUser;
            }

            @Override
            List<Trip> findTripsByUser(User user) {
                return tripListForSpecifiedUser;
            }
        };

        @BeforeEach
        void setUp() {
            when(specifiedUser.trips()).thenReturn(tripListForSpecifiedUser);
        }

        @Test
        @DisplayName("throws UserNotLoggedInException when session user is not logged in")
        void throws_UserNotLoggedInException_when_session_user_is_not_logged_in() {
            mockedSessionUser = null;
            assertThrows(UserNotLoggedInException.class, () ->
                    tripService.getTripsByUser(specifiedUser));
        }

        @Test
        @DisplayName("returns no trips when session user is not a friend of the specified user")
        void returns_no_trips_when_session_user_is_not_a_friend_of_the_specified_user() {
            mockedSessionUser = sessionUser;
            when(specifiedUser.getFriends()).thenReturn(Collections.emptyList());
            List<Trip> trips = tripService.getTripsByUser(specifiedUser);
            assertThat(trips).isEmpty();
        }

        @Test
        @DisplayName("returns three trips when session user is a friend of the specified user")
        void returns_three_trips_when_session_user_is_a_friend_of_the_specified_user() {
            mockedSessionUser = sessionUser;
            when(specifiedUser.getFriends()).thenReturn(Collections.singletonList(sessionUser));
            List<Trip> trips = tripService.getTripsByUser(specifiedUser);
            assertThat(trips).isEqualTo(tripListForSpecifiedUser);
        }
    }
}
