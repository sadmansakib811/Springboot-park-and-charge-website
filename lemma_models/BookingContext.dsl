bounded_context BookingContext {
    structure Booking {
        long id
        long userId
        long stationId
        String startTime
        String endTime
        double totalPrice
        String status // "PENDING", "ACCEPTED", "REJECTED"
    }
}
