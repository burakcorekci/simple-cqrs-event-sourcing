# Extremely Simple CQRS/Event Sourcing Project
This project is developed as an extremely simple CQRS/Event Sourcing example in 1.5 days for Elinvar. Because I currently do not have my personal computer with me, I did not have access to some of the things that I did, and would be comfortable with sharing with you. What I decided was to experience Guice, CQRS and Event Sourcing for myself, and make a small project out of that for you guys.

## Short Description
We have a bank system that allows for the following activities:
- Creation of accounts
- Deposit into account
- Withdraw from account
- Transfer to another account

The project is developed with a CQRS/Event Sourcing approach, but unfortunately it does not use versioning. While it allows for a margin of error in the case of concurrent operations, I hope that using Kafka partitioning based on account identifier will combat against it (but it will not completely eliminate it). As a partition can be processed by only one consumer, we will not have concurrent operations messing each other's work.

### Modules
#### bank-account-write
- Responsible for all the "write" events.
- Developed with Guice for dependency injection.
- Opens REST endpoints for account creation, deposit, withdrawal and transfer.
- Checks with **bank-account-read** module to determine whether a request is valid or not.
- Creates "events" from operations and publishes them into Kafka.

#### event-stream-processing
- Consumes the stream of events, parses them and persists the effect in the MySQL database.
- I initially tried to use Kafka Streams, but I was not able to persist effects into MySQL.

#### bank-account-read
- Responsible for the "read" operations.
- Opens REST endpoints for finding accounts by id, email or name.

## Things that I did not try before doing this project
- Read about CQRS/Event Sourcing, but never tried it before.
- I was familiar with DI from Spring, but never tried Guice before.
- I did stream processing with Kafka using Python, not Java.

## TODOs
- There are no tests at all.
- I do not like what I did with event parsing in the event-stream-processing module. I should find a better approach than if/else.
- No configuration files in event-stream-processing and bank-account-read modules.
- Better configuration solution, instead of a hand built one.
- Some parts (Entities, Repositories) should be pushed into commons.