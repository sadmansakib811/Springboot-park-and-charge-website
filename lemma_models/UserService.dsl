context UserContext {
    microservice UserService {
        operation createUser(User user)
        operation getUserById(long id) returns User
        operation getAllUsers() returns List<User>
        operation updateUser(User user)
        operation deleteUser(long id)
    }
}
