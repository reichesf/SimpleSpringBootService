#!/bin/sh
#---------------------------------------------------------------------
# item.sh
#
# Shell script used to interact with the service.
#---------------------------------------------------------------------

usage()
{
	echo ""
	echo "Usage: item.sh [-v] [-t mediaType] [-h host] [-p port]"
	echo "               [-l]"
	echo ""
	echo "           -l = List all items."
	echo "           -g = Get item specified by the UPC."
	echo "           -u = The UPC."
	echo ""
}

VERBOSE=
MEDIA_TYPE="application/xml"
HOST="localhost"
PORT="8080"
LISTITEMS="FALSE"
GETITEM="FALSE"
UPC=

while [ "$#" -gt 0 ]
do
	case $1 in
		-v) VERBOSE="-v" ; shift;;
		-h) shift ; HOST="$1" ; shift;;
		-p) shift ; PORT="$1" ; shift;;
		-t) shift ; MEDIA_TYPE="$1" ; shift;;
		-l) LISTITEMS="TRUE" ; shift;;
		-g) GETITEM="TRUE" ; shift;;
		-u) shift ; UPC="$1" ; shift;;
		*)  usage; exit 1;;
	esac
done

RESOURCE="item"

if [ "${GETITEM}" = "TRUE" ]
then
	RESOURCE="${RESOURCE}/${UPC}"
fi

curl ${VERBOSE}  \
	-X GET \
	--header accept:${MEDIA_TYPE} \
	http://${HOST}:${PORT}/${RESOURCE}

echo ""
