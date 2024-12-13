``profile``

is used for separate environment
such as:
At local you want to use local environment
at production you want to use production environment
=> profile comes in to separate it
only using application-dev.properties -> for dev
            application-prod.properties -> for production
            application.properties -> default properties for setting what profile is used
@Profile('name profile') -> allow if bean is created, if name profile in round brackets equal
name profile is define in application.properties -> bean that is defined will be created
else it isn't created
