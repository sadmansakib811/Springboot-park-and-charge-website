package com.infiniteloopers.booking.controller;

import com.infiniteloopers.booking.model.Booking;
import com.infiniteloopers.booking.model.BookingStatus;
import com.infiniteloopers.booking.model.Station;
import com.infiniteloopers.booking.service.BookingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Controller
@RequestMapping("/bookings")
public class BookingController {

    @Autowired
    private BookingService bookingService;

    @Autowired
    private RestTemplate restTemplate;

    @GetMapping
    public String getAllBookings(Model model) {
        List<Booking> bookings = bookingService.getAllBookings();
        model.addAttribute("bookings", bookings);
        return "bookingList";
    }

    @GetMapping("/create")
    public String showCreateBookingForm(Model model) {
        model.addAttribute("booking", new Booking());
        Station[] stations = restTemplate.getForObject("http://localhost:8082/stations/all", Station[].class);
        model.addAttribute("stations", stations);
        return "createBooking";
    }

    @PostMapping("/create")
    public String createBooking(@ModelAttribute Booking booking) {
        bookingService.saveBooking(booking);
        return "redirect:/bookings";
    }

    @GetMapping("/edit/{id}")
    public String showEditBookingForm(@PathVariable Long id, Model model) {
        Booking booking = bookingService.getBookingById(id);
        model.addAttribute("booking", booking);
        return "editBooking";
    }

    @PostMapping("/update")
    public String updateBooking(@ModelAttribute Booking booking) {
        bookingService.saveBooking(booking);
        return "redirect:/bookings";
    }

    @PostMapping("/delete/{id}")
    public String deleteBooking(@PathVariable Long id) {
        System.out.println("Attempting to delete booking with ID: " + id);
        Booking booking = bookingService.getBookingById(id);
        if (booking != null) {
            bookingService.deleteBooking(id);
            System.out.println("Booking deleted successfully with ID: " + id);
        } else {
            System.out.println("Booking not found for ID: " + id);
        }
        return "redirect:/bookings";
    }
    

    @PostMapping("/accept/{id}")
    public String acceptBooking(@PathVariable Long id) {
        bookingService.updateBookingStatus(id, BookingStatus.ACCEPTED);
        return "redirect:/bookings";
    }

    @PostMapping("/reject/{id}")
    public String rejectBooking(@PathVariable Long id) {
        bookingService.updateBookingStatus(id, BookingStatus.REJECTED);
        return "redirect:/bookings";
    }

    @GetMapping("/{id}")
    @ResponseBody
    public Booking getBookingById(@PathVariable Long id) {
        return bookingService.getBookingById(id);
    }

    @GetMapping("/station")
    @ResponseBody
    public List<Booking> getBookingsByStationId(@RequestParam Long stationId) {
        return bookingService.getBookingsByStationId(stationId);
    }

    @PostMapping("/updateStatus/{bookingId}/{status}")
    public String updateBookingStatus(@PathVariable Long bookingId, @PathVariable BookingStatus status) {
        bookingService.updateBookingStatus(bookingId, status);
        return "redirect:/bookings";
    }
}
