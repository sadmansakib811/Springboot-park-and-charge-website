bounded_context UserContext {
    structure User {
        long id
        String username
        String password
        String role // "CAR_OWNER" or "STATION_OWNER"
    }
}
