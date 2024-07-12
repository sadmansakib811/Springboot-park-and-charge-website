context StationContext {
    microservice StationService {
        operation createStation(Station station)
        operation getStationById(long id) returns Station
        operation getAllStations() returns List<Station>
        operation updateStation(Station station)
        operation deleteStation(long id)
    }
}
