# OFDM System (The Project)
A Java object-oriented model to solve the Incremental Routing, Modulation and Spectrum Assignment (RMSA) problem through heuristic simulations. The project was made using maven's build tool. It doesn't have any graphical interface and all of the options are accessible through the configuration files in the /resources/config directory. The whole system was made with scalability in mind and is possible, not only to implement new heuristics but to scale the problem to the routing, modulation, link and spectrum assignment (RMLSA), in the future.

# Development
This project was developed by Msc. Leonardo Almeida Jacobina Mesquita, supervisioned by the Ph.D Karcius Rosário de Assis, during the electrical engineering master's course at the Federal University of Bahia (Universidade Federal da Bahia - UFBA).

# Configurations

The set of options for the simulation can be altered in the configuration files inside folder /resources/config

## Networks Options

It is possible to add new networks by adding it to the networks.json file. With the proper informartion and paths to the files that describe the network in either cost matrix form or edge description form. it is recommended to keep the network files inside the resources/graphs folder. If the "enabled" field is set to true, simulations will automatically run for that network.

## Modulation Options

You can add modulation formats to be used by the RMSA algorithms by adding them to the modulation.json file. If the modulation format is added and the field "enabled" is set to true, then to the .json file, it will automatically be considered during the RMSA.

## Fiber Link Options

The number of subcarriers, number of fibers per source-destination pair, and bandwidth per subscarrier can be modified in the fiberlinks.yaml file.

## Heuristic Options

When it comes to the heuristics, the path-finding and routing options like number of calculated routes per demand, guardband between allocated channels, maximum number of hops between nodes and traffic sorting can all be edited in the heuristics.yaml file. This configuration file has the option for printing a the step-by-step solution on the console with the "printingEnabled" variable. It can also print the same report on a text file with the "writingEnabled" variable.

## Traffic Options

It is possible to configure the simulated traffic behaviour using the traffics.yaml file. It has access to the number of time steps simulated. If you want to use a generic traffic matrix, yo ucan simply set the "customTraffic" variable to false and set the "initiDemand" variable with the value of the demand, in Gbps, to be sente from every noe to every other node. If "timeStep" is greater than one, then, for every time-step, the initDemand will be increased by a factor set in the "incRate" variable. 
If you want to use a custom traffic matrix, that is "customTraffic" set to true, the program will look for the custom traffic matrix in the network's folder inside resources/graphs. The name of the file must be "traffic-matrix.txt", or an error will be thrown.


