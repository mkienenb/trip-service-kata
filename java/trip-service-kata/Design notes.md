# Design Notes
- TripService.getTripsByUser()
  - Requires a non-null User
  - identifies loggedIn user
  - throws UserNotLoggedInException if no loggedInUser.
  - checks if loggedIn user is a friend of the target User
  - if friend, returns trips of the target user
  - otherwise returns empty list
## Design issues
  - Logged-in check isn't clearly separated from rest of code, making code difficult to read.
## Testing issues
- UserSession is singleton
- TripDAO is a singleton
- TripDAO methods are static
