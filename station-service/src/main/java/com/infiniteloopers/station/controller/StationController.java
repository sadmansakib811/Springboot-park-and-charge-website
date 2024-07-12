package com.infiniteloopers.station.controller;

import com.infiniteloopers.station.model.Booking;
import com.infiniteloopers.station.model.Station;
import com.infiniteloopers.station.service.StationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/stations")
public class StationController {

    @Autowired
    private StationService stationService;

    @GetMapping
    public String getAllStations(Model model) {
        List<Station> stations = stationService.getAllStations();
        model.addAttribute("stations", stations);
        return "stationList";
    }

    @GetMapping("/create")
    public String showCreateStationForm(Model model) {
        model.addAttribute("station", new Station());
        return "createStation";
    }
    @GetMapping("/edit/{id}")
    public String showEditStationForm(@PathVariable Long id, Model model) {
        Station station = stationService.getStationById(id);
        if (station != null) {
            model.addAttribute("station", station);
            return "editStation";
        }
        return "redirect:/stations";
    }

    @PostMapping("/create")
    public String createStation(@ModelAttribute Station station) {
        stationService.saveStation(station);
        return "redirect:/stations";
    }
    @PostMapping("/delete/{id}")
    public String deleteStation(@PathVariable Long id) {
        stationService.deleteStation(id);
        return "redirect:/stations";
    }
    @PostMapping("/update")
    public String updateStation(@ModelAttribute Station station) {
        stationService.saveStation(station);
        return "redirect:/stations";
    }
    @GetMapping("/all")
    @ResponseBody
    public List<Station> getAllStationsJson() {
        return stationService.getAllStations();
    }

    @GetMapping("/{id}")
    @ResponseBody
    public Station getStationById(@PathVariable Long id) {
        return stationService.getStationById(id);
    }

    @GetMapping("/manageBookings/{stationId}")
    public String manageBookings(@PathVariable Long stationId, Model model) {
        try {
            List<Booking> bookings = stationService.getBookingsByStationId(stationId);
            model.addAttribute("bookings", bookings);
            model.addAttribute("stationId", stationId);
            System.out.println("Bookings: " + bookings);  // Log bookings
            return "manageBookings";
        } catch (Exception e) {
            e.printStackTrace();  // Log the exception
            return "error";
        }
    }

    @PostMapping("/{stationId}/updateStatus/{bookingId}/{status}")
    public String updateBookingStatus(@PathVariable Long stationId, @PathVariable Long bookingId, @PathVariable String status, Model model) {
        try {
            stationService.updateBookingStatus(bookingId, status);
            model.addAttribute("stationId", stationId);
            return "redirect:/stations/manageBookings/" + stationId;
        } catch (Exception e) {
            e.printStackTrace();
            return "error";
        }
    }
}
