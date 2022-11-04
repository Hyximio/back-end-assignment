package com.mmbackendassignment.mmbackendassignment.config;

import com.github.javafaker.Faker;
import com.mmbackendassignment.mmbackendassignment.model.*;
import com.mmbackendassignment.mmbackendassignment.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.*;

@Component
public class PopulatingDatabase {

    @Autowired
    public RoleRepository roleRepo;

    @Autowired
    public FieldRepository fieldRepo;

    @Autowired
    public AddressRepository addressRepo;

    @Autowired
    public OwnerRepository ownerRepo;

    @Autowired
    public ClientRepository clientRepo;

    @Autowired
    public ProfileRepository profileRepo;

    @Autowired
    public UserRepository userRepo;

    @Autowired
    public PasswordEncoder encoder;

    @PostConstruct
    public void setup(){
        this.addRoles();
        this.createAdmin();
        this.populateDatabase(100);
    }

    public void createAdmin(){

        List<User> existUsers = userRepo.findAll();
        boolean adminUserExist = false;

        for( User user : existUsers ){
            if( user.getUsername().equals("admin") ) {
                adminUserExist = true;
                break;
            }
        }

        if (!adminUserExist){
            User adminUser = new User( "admin", "$2a$10$db44mu0m6Fg8edeT5WYd2OsJM/GBuG3qJ3rQ9h/v/5SqkIVfiU8ke" ); // pw:Admin123
            adminUser.addRole("CLIENT");
            adminUser.addRole("ADMIN");
            userRepo.save( adminUser );
            System.out.println( " *** Admin user added (username:'admin' pw:'Admin123') ***");
        }
    }


    public void addRoles(){

        List<Role> existRoles = roleRepo.findAll();

        List<String> roles = Arrays.asList( "CLIENT", "OWNER", "ADMIN" );

        for( String r : roles ){
            if ( !existRoles.contains(r) ){
                roleRepo.save( new Role(r) );
            }
        }
    }

    public void populateDatabase( int fillUntil ){

        int toBeAdded = fillUntil - userRepo.findAll().size();

        if( toBeAdded < 1) return;

        System.out.println( " *** Populating: " + toBeAdded + " users will be populated in database to an amount of " + fillUntil);

        Faker faker = new Faker();
        Random rand = new Random();

        for( int i = 0; i < toBeAdded; i++ ){

            String password = faker.regexify("[A-Z]{1}[a-z]{6}[0-9]{2}");
            User user = new User( faker.name().username(), encoder.encode( password ) );
            user.setEnabled( rand.nextFloat() < 0.8 );

            Profile profile = new Profile();
            profile.setEmail( faker.bothify("????##@email.com"));
            profile.setDob( dateToLocalDate( faker.date().birthday(18,65)) );
            profile.setFirstName( faker.name().firstName() );
            profile.setGender( faker.regexify("m|v").charAt(0) );
            profile.setLastName( faker.name().lastName() );
            profile.setPhoneNumber( faker.regexify("+316[0-9]{8}") );

            User savedUser = userRepo.save( user );

            Client client = new Client();
            client.setProfile(profile);
            Client savedClient = clientRepo.save( client );

            profile.setUser( savedUser );
            profile.setClient( savedClient );

            profileRepo.save(profile);

            if (rand.nextFloat() < 0.4){

                Address address = new Address();
                address.setStreet( faker.address().streetName() );
                address.setNumber( faker.number().numberBetween(1, 150));
                address.setPostalCode( faker.regexify( "[1-9]{4}[A-Z]{2}"));
                address.setCity( faker.address().city() );
                address.setCountry( "Netherlands" );

                Owner owner = new Owner();
                owner.setProfile( profile );
                ownerRepo.save( owner );

                profile.setOwner( owner );
                profileRepo.save( profile );

                savedUser.addRole("OWNER");
                userRepo.save( savedUser );

                address.setOwner( owner );
                addressRepo.save( address );

                int amountOfFields = (rand.nextFloat() < 0.4) ? 1 : 2;
                for( int f = 0; f < amountOfFields; f++ ){
                    Field field = new Field();

                    field.setDescription( faker.lorem().sentence(10, 20));
                    field.setMeters( (float)faker.number().randomDouble(1,1,20) );
                    field.setMaxHeightMeter( (float)faker.number().randomDouble(1,1,3) );
                    field.setPricePerMonth((float)faker.number().randomDouble(1,20,150));
                    field.setAddress( address );

                    int amountOfFeatures = (int)Math.floor(rand.nextFloat() * 4) + 1;
                    ArrayList<String> features = new ArrayList<>();
                    for( int m = 0; m < amountOfFeatures; m++ ){
                        features.add( faker.food().vegetable());
                    }
                    field.setFeatures( features );

                    Field savedField = fieldRepo.save( field );
                    address.addField( savedField );
                    addressRepo.save( address );

                }
            }
        }

        System.out.println(" *** Succesfully filled the database ***" );
    }

    private LocalDate dateToLocalDate( Date date ) {
        return date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
    }
}
