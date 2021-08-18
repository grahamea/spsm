
CATALINA_OPTS="-Dcom.sun.management.jmxremote.ssl=false -Dcom.sun.management.jmxremote.authenticate=false -Dcom.sun.management.jmxremote.port=9999 -Xmx1024M"

CATALINA_PID="$CATALINA_HOME/catalina_pid.txt"

export CATALINA_OPTS CATALINA_PID

