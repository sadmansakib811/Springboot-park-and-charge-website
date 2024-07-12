context BookingContext {
    microservice BookingService {
        operation createBooking(Booking booking)
        operation getBookingById(long id) returns Booking
        operation getAllBookings() returns List<Booking>
        operation updateBooking(Booking booking)
        operation deleteBooking(long id)
    }
}
