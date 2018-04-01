#!/bin/sh
#---------------------------------------------------------------------
# AddItem.sh
#
# Shell script used to add an Item.
#---------------------------------------------------------------------

usage()
{
	echo ""
	echo "Usage: AddItem [-v] [-t mediaType] [-h host] [-p port]"
	echo "               -u <upc>"
	echo "               -d <description>]"
	echo "               -q <quantity>j"
	echo ""
}

VERBOSE=
MEDIA_TYPE="application/xml"
HOST="localhost"
PORT="8080"

UPC=
DESCRIPTION=
QUANTITY=

CORRELATION_ID=`uuidgen`
SOURCE_APPLICATION="$0"
EVENT="AddItem"

while [ "$#" -gt 0 ]
do
	case $1 in
		-v) VERBOSE="-v" ; shift;;
		-h) shift ; HOST="$1" ; shift ;;
		-p) shift ; PORT="$1" ; shift ;;
		-t) shift ; MEDIA_TYPE="$1" ; shift ;;
		-u) shift ; UPC="$1" ; shift ;;
		-d) shift ; DESCRIPTION="$1" ; shift ;;
		-q) shift ; QUANTITY="$1" ; shift ;;
		*)  usage; exit 1;;
	esac
done

if [ "${UPC}" = "" -o "${DESCRIPTION}" = "" -o "${QUANTITY}" = "" ]
then
	usage
	exit 1
fi

# Setup the request that will be passed.

REQUEST_FILE="/tmp/request-$$"

rm -rf ${REQUEST_FILE}

if [ "${MEDIA_TYPE}" = "application/json" ]
then
	echo "{" > ${REQUEST_FILE}
	echo -n "  \"upc\": \"${UPC}\"" >> ${REQUEST_FILE}
	echo "," >> ${REQUEST_FILE}
	echo -n "  \"description\": \"${DESCRIPTION}\"" >> ${REQUEST_FILE}
	echo "," >> ${REQUEST_FILE}
	echo -n "  \"quantity\": \"${QUANTITY}\"" >> ${REQUEST_FILE}
	echo "" >> ${REQUEST_FILE}
	echo "}" >> ${REQUEST_FILE}
else
	echo "<Item>" > ${REQUEST_FILE}
	echo "  <upc>${UPC}</upc>" >> ${REQUEST_FILE}
	echo "  <description>${DESCRIPTION}</description>" >> ${REQUEST_FILE}
	echo "  <quantity>${QUANTITY}</quantity>" >> ${REQUEST_FILE}
	echo "</Item>" >> ${REQUEST_FILE}
fi

# Setup the Resource that will be accessed.

RESOURCE="item"

if [ "${VERBOSE}" != "" ]
then
	echo "Sending request:"
	echo "----------------------------------------------------"
	cat ${REQUEST_FILE}
	echo "----------------------------------------------------"
fi

#curl ${VERBOSE}  \
#	-X PUT --data @${REQUEST_FILE} \
#	--header content-type:${MEDIA_TYPE} \
#	--header accept:${MEDIA_TYPE} \
#	--header X-Correlation-Id:${CORRELATION_ID} \
#	--header X-Source-Application:${SOURCE_APPLICATION} \
#	--header X-Event-Name:${EVENT} \
#	http://${HOST}:${PORT}/${RESOURCE}

rm -f ${REQUEST_FILE}

echo ""
