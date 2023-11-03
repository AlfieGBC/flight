// ICanValidate Interface
interface ICanValidate {
    fun isValid(): Boolean
}

// Traveller Class
class Traveller(
    val name: String,
    var passportNumber: Int,
    val emailAddress: String
) : ICanValidate {
    override fun isValid(): Boolean {
        return passportNumber in 10000..99999
    }

    override fun toString(): String {
        return "Passenger: $name\nEmail address: $emailAddress\nPassport number: $passportNumber\n"
    }
}

// Flight Class
open class Flight(
    val flightNumber: String,
    val departureAirport: String,
    val arrivalAirport: String,
    var flightDistance: Double
) {
    override fun toString(): String {
        return "Flight Information: Flight: $flightNumber\n" +
                "Departure Airport: $departureAirport\n" +
                "Arrival Airport: $arrivalAirport\n" +
                "Distance: $flightDistance"
    }
}
class Reservation(
    val traveller: Traveller,  // 修改為 traveller
    var seatNumber: String,
    flight: Flight
) : Flight(flight.flightNumber, flight.departureAirport, flight.arrivalAirport, flight.flightDistance) {
    val reservationId: Int = (1000..9999).random()

    fun calculateCost(): Double {
        return 100 + (flightDistance * 0.12)
    }

    override fun toString(): String {
        return "Passenger: ${traveller.name}\n" +
                "Reservation id: $reservationId\n" +
                super.toString() + "\n" +
                "Seat number: $seatNumber"
    }
}


fun main() {
    // Create empty lists for travellers, flights, and reservations
    val travellers = mutableListOf<Traveller>()
    val flights = mutableListOf<Flight>()
    val reservations = mutableListOf<Reservation>()

    // Populate the lists with travellers and flights
    travellers.add(Traveller("Bob Smith", 10000, "bob@gmail.com"))
    travellers.add(Traveller("Abigail Diaz", 9999, "abigail@gmail.com"))
    travellers.add(Traveller("Carmen Pope", 20000, "carmen@gmail.com"))

    flights.add(Flight("AA281", "DFW", "ICN", 6835.70))
    flights.add(Flight("AC306", "PHX", "YUL", 2291.00))
    flights.add(Flight("WN3855", "PHX", "STL", 1261.38))

    // Section 1: Searching for a Traveller
    var travellerFound = false
    for (traveller in travellers) {
        if (traveller.name.contains("Smith")) {
            println("------------ Searching for a Traveller ----------")
            println("Traveller found.")
            println(traveller)
            travellerFound = true
            break
        }
    }
    if (!travellerFound) {
        println("No matching travellers found")
    }

    // Section 2: Is the passport valid?
    val abigail = travellers.find { it.name == "Abigail Diaz" }
    if (abigail != null) {
        println("------------ CHECKING PASSPORT ----------")
        if (abigail.isValid()) {
            println("Abigail's passport is valid")
        } else {
            println("Abigail's passport is invalid")
            abigail.passportNumber = 10000
        }
        println()
    }

    // Section 3: Creating Reservations
    val bobReservation1 = Reservation(travellers[0], "3B", flights[2])
    val bobReservation2 = Reservation(travellers[0], "15C", flights[0])
    val carmenReservation = Reservation(travellers[2], "8A", flights[1])

    reservations.add(bobReservation1)
    reservations.add(bobReservation2)
    reservations.add(carmenReservation)

    // Section 4: Outputting Information about Reservations
    val phxDepartures = reservations.filter { it.departureAirport == "PHX" }
    val totalRevenue = reservations.sumByDouble { it.calculateCost() }

    println("------------ RESERVATIONS INFORMATION ----------")
    println("Number of reservations departing PHX: ${phxDepartures.size}")
    println("Total revenue from reservations: $$totalRevenue")
    println()

    // Section 5: Modifying a Reservation
    println("------------ CARMEN'S RESERVATION ----------")
    println(carmenReservation)
    println()

    val oldCost = carmenReservation.calculateCost()
    println("------------ UPDATED RESERVATION ----------")
// Modify the seat number and flight distance
    carmenReservation.seatNumber = "999Q"  // 更新座位號碼
    carmenReservation.flightDistance = 1832.8
// Recalculate the cost after the flight distance changed
    println(carmenReservation)
    println("Old reservation cost: $$oldCost")
    println("Updated reservation cost: $${carmenReservation.calculateCost()}")
}
