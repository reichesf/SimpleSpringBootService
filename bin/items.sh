#!/bin/sh
#---------------------------------------------------------------------
# items.sh
#
# Shell script used to interact with the service.
#---------------------------------------------------------------------

usage()
{
	echo ""
	echo "Usage:  items.sh [-v] [-t mediaType] [-h host] [-p port]"
	echo "                 [-l | -g | -A | -U] [-f file]"
	echo "                 [-u upc] [-d description] [-b balance]"
	echo ""
	echo "     -l = List all items."
	echo "     -g = Get item specified by the UPC."
	echo "     -A = Add the item; upc, description, balance are required."
	echo "     -U = Update the item; upc, description, balance are required."
	echo "     -f = File containing the items to add/update."
	echo "     -u = The UPC."
	echo "     -d = Description."
	echo "     -b = Balance."
	echo ""
}

VERBOSE=
MEDIA_TYPE="application/xml"
HOST="localhost"
PORT="8080"
DATA=
CONTENT_TYPE=
REQUEST_FILE=
LISTITEMS="FALSE"
GETITEM="FALSE"
ADDITEM="FALSE"
UPDATEITEM="FALSE"
UPC=
DESCRIPTION=
BALANCE=
FILE=

while [ "$#" -gt 0 ]
do
	case $1 in
		-v) VERBOSE="-v" ; shift;;
		-h) shift ; HOST="$1" ; shift;;
		-p) shift ; PORT="$1" ; shift;;
		-t) shift ; MEDIA_TYPE="$1" ; shift;;
		-l) LISTITEMS="TRUE" ; shift;;
		-g) GETITEM="TRUE" ; shift;;
		-A) ADDITEM="TRUE" ; shift;;
		-U) UPDATEITEM="TRUE" ; shift;;
		-f) shift ; FILE=$1 ; shift;;
		-u) shift ; UPC="$1" ; shift;;
		-d) shift ; DESCRIPTION="$1" ; shift;;
		-b) shift ; BALANCE="$1" ; shift;;
		*)  usage; exit 1;;
	esac
done

# Setup the request that will be passed.

REQUEST_FILE="/tmp/request-$$"

rm -rf ${REQUEST_FILE}

RESOURCE="v1/items"

if [ "${GETITEM}" = "TRUE" ]
then
	if [ "${UPC}" = "" ]
	then
		usage
		exit 1
	fi
	RESOURCE="${RESOURCE}/${UPC}"
	METHOD="GET"

elif [ "${ADDITEM}" = "TRUE" -o "${UPDATEITEM}" = "TRUE" ]
then
	if [ "${FILE}" = "" ]
	then
		if [ "${UPC}" = "" -o "${DESCRIPTION}" = "" -o "${BALANCE}" = ""  ]
		then
			usage
			exit 1
		fi
	fi
	if [ "${ADDITEM}" = "TRUE" ]
	then
		METHOD="POST"
	else
		METHOD="PUT"
	fi
	if [ "${FILE}" = "" ]
	then
		if [ "${MEDIA_TYPE}" = "application/json" ]
		then
			echo "{" > ${REQUEST_FILE}
			echo "  \"itemList\": [" >> ${REQUEST_FILE}
			echo "    {" >> ${REQUEST_FILE}
			echo "      \"upc\": \"${UPC}\"," >> ${REQUEST_FILE}
			echo "      \"description\": \"${DESCRIPTION}\"," >> ${REQUEST_FILE}
			echo "      \"balance\": \"${BALANCE}\"" >> ${REQUEST_FILE}
			echo "    }" >> ${REQUEST_FILE}
			echo "  ]" >> ${REQUEST_FILE}
			echo "}" >> ${REQUEST_FILE}
		else
			echo "<ItemList>" > ${REQUEST_FILE}
			echo "  <Item>" >> ${REQUEST_FILE}
			echo "    <upc>${UPC}</upc>" >> ${REQUEST_FILE}
			echo "    <description>${DESCRIPTION}</description>" >> ${REQUEST_FILE}
			echo "    <balance>${BALANCE}</balance>" >> ${REQUEST_FILE}
			echo "  </Item>" >> ${REQUEST_FILE}
			echo "</ItemList>" >> ${REQUEST_FILE}
		fi
		DATA="--data @${REQUEST_FILE}"
	else
		DATA="--data @${FILE}"
	fi
	CONTENT_TYPE="--header content-type:${MEDIA_TYPE}"

elif [ "${LISTITEMS}" = "TRUE" ]
then
	METHOD="GET"
#	RESOURCE="${RESOURCE}/"
else
	usage
	exit 1
fi

if [ "${VERBOSE}" != "" ]
then
	if [ "${ADDITEM}" = "TRUE" -o "${UPDATEITEM}" = "TRUE" ]
	then
		echo "Sending request:"
		echo "----------------------------------------------------"
		cat ${REQUEST_FILE}
		echo "----------------------------------------------------"
	fi
fi

curl ${VERBOSE}  \
	-X ${METHOD} ${DATA} ${CONTENT_TYPE} \
	--header accept:${MEDIA_TYPE} \
	http://${HOST}:${PORT}/${RESOURCE}

rm -f ${REQUEST_FILE}

echo ""
