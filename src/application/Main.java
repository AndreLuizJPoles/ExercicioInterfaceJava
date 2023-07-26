package application;

import model.entities.CarRental;
import model.entities.Vehicle;
import model.exceptions.CarRentalException;
import model.services.BrazilTaxService;
import model.services.RentalService;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.InputMismatchException;
import java.util.Locale;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Locale.setDefault(Locale.US);
        Scanner sc = new Scanner(System.in);
        DateTimeFormatter fmt = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");

        try {
            System.out.println("Entre com os dados do aluguel");
            System.out.print("Modelo do carro: ");
            String carModel = sc.nextLine();
            System.out.print("Retirada (dd/MM/yyyy hh:mm): ");
            LocalDateTime start = LocalDateTime.parse(sc.nextLine(), fmt);
            System.out.print("Retorno (dd/MM/yyyy hh:mm): ");
            LocalDateTime finish = LocalDateTime.parse(sc.nextLine(), fmt);

            CarRental carRental = new CarRental(start, finish, new Vehicle(carModel));

            System.out.print("Entre com o preço por hora: ");
            double pricePerHour = sc.nextDouble();
            System.out.print("Entre com o preço por dia: ");
            double pricePerDay = sc.nextDouble();

            RentalService rentalService = new RentalService(pricePerHour, pricePerDay, new BrazilTaxService());

            rentalService.processInvoice(carRental);

            System.out.println("\nFATURA:");
            System.out.print("Pagamento básico: " + String.format("%.2f", carRental.getInvoice().getBasicPayment()) + "\n");
            System.out.print("Imposto: " + String.format("%.2f", carRental.getInvoice().getTax()) + "\n");
            System.out.print("Pagamento total: " + String.format("%.2f", carRental.getInvoice().getTotalPayment()) + "\n");
        }
        catch(InputMismatchException e){
            System.out.println("Erro: " + e.getMessage());
        }
        catch(CarRentalException e){
            System.out.println("Erro: " + e.getMessage());
        }
        catch(DateTimeParseException e){
            System.out.println("Erro: " + e.getMessage());
        }
        finally {
            sc.close();
        }
    }
}