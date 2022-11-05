package com.mmbackendassignment.mmbackendassignment.config;

import com.github.javafaker.Faker;
import com.mmbackendassignment.mmbackendassignment.model.*;
import com.mmbackendassignment.mmbackendassignment.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
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

    @Value(("${database.autofill}") )
    boolean autofill;

    @Value(("${database.fill-database-to-an-amount-of}") )
    long fillUntil;

    @Value(("${database.percentage-of-users-with-addresses}") )
    long withAddress;

    @PostConstruct
    public void setup(){

        this.addRoles();
        this.createAdmin();
        this.createClient();
        this.createOwner();

        if( autofill ) {
            this.populateDatabase(fillUntil, withAddress);
        }
    }

    private void createAdmin(){

        List<User> existUsers = userRepo.findAll();
        boolean userExist = false;

        for( User user : existUsers ){
            if( user.getUsername().equals("admin") ) {
                userExist = true;
                break;
            }
        }

        if (!userExist){
            User adminUser = new User( "admin", "$2a$10$db44mu0m6Fg8edeT5WYd2OsJM/GBuG3qJ3rQ9h/v/5SqkIVfiU8ke" ); // pw:Admin123
            adminUser.addRole("CLIENT");
            adminUser.addRole("ADMIN");
            userRepo.save( adminUser );
            System.out.println( " *** Admin user added (username:'admin' pw:'Admin123') ***");
        }
    }


    private void createClient(){

        List<User> existUsers = userRepo.findAll();
        boolean userExist = false;

        for( User user : existUsers ){
            if( user.getUsername().equals("client") ) {
                userExist = true;
                break;
            }
        }

        if (!userExist) {
            User adminUser = new User("client", "$2a$12$NogvR8AmFHA7lp5efW3IEOXHJCYs6xdLB7FS3o8jxlyNoLihAQQfC"); // pw:Client123
            adminUser.addRole("CLIENT");

            User savedUser = userRepo.save(adminUser);

            Faker faker = new Faker();
            Profile profile = createRandomProfile( faker );

            Client client = new Client();
            client.setProfile(profile);
            Client savedClient = clientRepo.save( client );

            profile.setUser( savedUser );
            profile.setClient( savedClient );

            profileRepo.save(profile);

            System.out.println( " *** Client user added (username:'client' pw:'Client123') ***");
        }
    }

    private void createOwner(){
        List<User> existUsers = userRepo.findAll();
        boolean userExist = false;

        for( User user : existUsers ){
            if( user.getUsername().equals("owner") ) {
                userExist = true;
                break;
            }
        }

        if (!userExist) {
            User adminUser = new User("owner", "$2a$12$N8w9O..Xjl0E60SZz6uYYu3uo5xdtRaZSdFD4Jc3Tetn4.n1T/mcy"); // pw:Owner123
            adminUser.addRole("CLIENT");
            adminUser.addRole("OWNER");

            User savedUser = userRepo.save(adminUser);

            Faker faker = new Faker();
            Profile profile = createRandomProfile( faker );

            Client client = new Client();
            client.setProfile(profile);
            Client savedClient = clientRepo.save( client );

            profile.setUser( savedUser );
            profile.setClient( savedClient );

            Address address = createRandomAddress( faker );

            Owner owner = new Owner();
            owner.setProfile( profile );

            ownerRepo.save( owner );

            profile.setOwner( owner );
            profileRepo.save( profile );

            address.setOwner( owner );
            Address savedAddress = addressRepo.save( address );

            Field field = createRandomField( faker );
            field.setAddress( savedAddress );

            Field savedField = fieldRepo.save( field );
            savedAddress.addField( savedField );
            addressRepo.save( savedAddress );

            System.out.println( " *** Owner user added (username:'owner' pw:'Owner123') ***");
        }
    }
    private void addRoles(){

        List<Role> existRoles = roleRepo.findAll();

        List<String> roles = Arrays.asList( "CLIENT", "OWNER", "ADMIN" );

        for( String r : roles ){
            if ( !existRoles.contains(r) ){
                roleRepo.save( new Role(r) );
            }
        }
    }

    private void populateDatabase( Long fillUntil, Long withAddress ){

        long dbSize = userRepo.findAll().size();
        long toBeAdded = fillUntil - dbSize;

        if( toBeAdded < 1) {
            System.out.println( " *** No auto-populating needed because the database already contains " + dbSize + " users" );
            return;
        }

        System.out.println( " *** Auto-Populating: " + toBeAdded + " users will be populated in database to an amount of " + fillUntil);
        System.out.println( " *** Generating users..." );

        Faker faker = new Faker();
        Random rand = new Random();

        for( int i = 0; i < toBeAdded; i++ ){

            String password = faker.regexify("[A-Z]{1}[a-z]{6}[0-9]{2}");
            User user = new User( faker.name().username(), encoder.encode( password ) );
            user.setEnabled( rand.nextFloat() < 0.8 );


            Profile profile = createRandomProfile( faker );

            User savedUser = userRepo.save( user );

            Client client = new Client();
            client.setProfile(profile);
            Client savedClient = clientRepo.save( client );

            profile.setUser( savedUser );
            profile.setClient( savedClient );

            profileRepo.save(profile);

            float percentageWithAddress = withAddress.floatValue() / 100;
            if ( rand.nextFloat() < percentageWithAddress ){

                Address address = createRandomAddress( faker );

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

                    Field field = createRandomField( faker );
                    field.setAddress( address );

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

    private Profile createRandomProfile( Faker faker ){
        Profile profile = new Profile();
        profile.setEmail( faker.bothify("????##@email.com"));
        profile.setDob( dateToLocalDate( faker.date().birthday(18,65)) );
        profile.setFirstName( faker.name().firstName() );
        profile.setGender( faker.regexify("m|v").charAt(0) );
        profile.setLastName( faker.name().lastName() );
        profile.setPhoneNumber( faker.regexify("+316[0-9]{8}") );

        return profile;
    }

    private Address createRandomAddress( Faker faker ){
        Address address = new Address();
        address.setStreet( faker.address().streetName() );
        address.setNumber( faker.number().numberBetween(1, 150));
        address.setPostalCode( faker.regexify( "[1-9]{4}[A-Z]{2}"));
        address.setCity( faker.address().city() );
        address.setCountry( "Netherlands" );

        return address;
    }

    private Field createRandomField( Faker faker ){
        Random rand = new Random();

        Field field = new Field();
        field.setDescription( faker.lorem().sentence(10, 20));
        field.setMeters( (float)faker.number().randomDouble(1,1,20) );
        field.setMaxHeightMeter( (float)faker.number().randomDouble(1,1,3) );
        field.setPricePerMonth((float)faker.number().randomDouble(1,20,150));

        int amountOfFeatures = (int)Math.floor(rand.nextFloat() * 4) + 1;
        ArrayList<String> features = new ArrayList<>();
        for( int m = 0; m < amountOfFeatures; m++ ){
            features.add( faker.food().vegetable());
        }
        field.setFeatures( features );

        return field;
    }
}
