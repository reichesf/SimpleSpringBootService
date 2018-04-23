package com.github.reichesf.itemservice;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@Api(value = "items", description = "Simple Item Service")
@RequestMapping("/v1")
public final class Controller
{
    //@Autowired
    private ItemService itemService;

    public Controller(ItemService itemService)
    {
        this.itemService = itemService;
    }

    // Illustrates the following:
    // - Exact specification of the method (GET) on the Request Mapping.
    // - Multiple "produces" on the RequestMapping to handle both JSON and XML.
    // - Use of the ResponseEntity when producing a response.

    @ApiOperation(value = "Get Item", notes = "Retrieves the specified item.")
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "OK"),
        @ApiResponse(code = 400, message = "Bad Request"),
        @ApiResponse(code = 401, message = "Unauthorized"),
        @ApiResponse(code = 404, message = "Not Found"),
        @ApiResponse(code = 409, message = "Conflict"),
        @ApiResponse(code = 503, message = "Service Unavailable"),
    })
    @RequestMapping(value = "items/{upc}", method = RequestMethod.GET,
            produces={MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE, MediaType.TEXT_XML_VALUE})
    public ResponseEntity<ItemList> getItem(@PathVariable String upc)
    {
        ResponseEntity<ItemList> responseEntity = null;
        ItemData itemData = null;
        ItemList itemList = null;

        try
        {
            if (upc == null)
            {
                responseEntity = new ResponseEntity<>(itemList, HttpStatus.BAD_REQUEST);
            }
            else
            {
                itemData = itemService.getItem(upc);

                if (itemData != null)
                {
                    itemList = new ItemList();
                    itemList.add(new Item(itemData));

                    responseEntity = new ResponseEntity<>(itemList, HttpStatus.OK);
                }
                else
                {
                    responseEntity = new ResponseEntity<>(HttpStatus.NO_CONTENT);
                }
            }
        }
        finally
        {
            itemData = null;
            itemList = null;
        }
        return responseEntity;
    }

    @ApiOperation(value = "Get Items", notes = "Retrieves the all items.")
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "OK"),
        @ApiResponse(code = 400, message = "Bad Request"),
        @ApiResponse(code = 401, message = "Unauthorized"),
        @ApiResponse(code = 404, message = "Not Found"),
        @ApiResponse(code = 409, message = "Conflict"),
        @ApiResponse(code = 503, message = "Service Unavailable"),
    })
    @RequestMapping(value = "items", method = RequestMethod.GET,
            produces={MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE, MediaType.TEXT_XML_VALUE})
    public ResponseEntity<ItemList> getItemList()
    {
        ResponseEntity<ItemList> responseEntity = null;
        ItemList itemList = null;
        ItemData itemData = null;

        try
        {
            itemList = new ItemList();

            for (String sUpc : itemService.getUpcList())
            {
                itemData = itemService.getItem(sUpc);

                if (itemData != null)
                {
                    itemList.add(new Item(itemData));
                }
            }
            if (itemList.isEmpty())
            {
                responseEntity = new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            else
            {
                responseEntity = new ResponseEntity<>(itemList, HttpStatus.OK);
            }
        }
        finally
        {
            itemData = null;
        }
        return responseEntity;
    }

    @ApiOperation(value = "Adds Items", notes = "Adds the specified items.")
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "OK"),
        @ApiResponse(code = 201, message = "Created"),
        @ApiResponse(code = 400, message = "Bad Request"),
        @ApiResponse(code = 401, message = "Unauthorized"),
        @ApiResponse(code = 403, message = "Forbidden"),
        @ApiResponse(code = 404, message = "Not Found"),
        @ApiResponse(code = 409, message = "Conflict"),
        @ApiResponse(code = 503, message = "Service Unavailable"),
    })
    @RequestMapping(value = "items", method = RequestMethod.POST,
            produces={MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE, MediaType.TEXT_XML_VALUE})
    public ResponseEntity<ItemListResponse> addItem(@RequestBody ItemList itemList)
    {
        ResponseEntity<ItemListResponse> responseEntity = null;
        ItemListResponse retItemListResponse = null;
        ItemResponse itemResponse = null;
        ItemData itemData = null;

        try
        {
            if (itemList == null)
            {
                responseEntity = new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            }
            else if (itemList.isEmpty())
            {
                responseEntity = new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            }
            else
            {
                retItemListResponse = new ItemListResponse();

                for (Item item : itemList.getItemList())
                {
                    itemData = new ItemData(item.getUpc(), item.getDescription(), item.getBalance());

                    itemResponse = new ItemResponse();

                    if (itemService.addItem(itemData) == true)
                    {
                        itemData = itemService.getItem(itemData.getUpc());

                        itemResponse.setUpc(itemData.getUpc());
                        itemResponse.setDescription(itemData.getDescription());
                        itemResponse.setBalance(itemData.getBalance());
                        itemResponse.setStatusCode(HttpStatus.CREATED.value());
                        itemResponse.setStatus(HttpStatus.CREATED.getReasonPhrase());
                    }
                    else
                    {
                        itemResponse.setUpc(item.getUpc());
                        itemResponse.setDescription(item.getDescription());
                        itemResponse.setBalance(item.getBalance());
                        itemResponse.setStatusCode(HttpStatus.FORBIDDEN.value());
                        itemResponse.setStatus(HttpStatus.FORBIDDEN.getReasonPhrase());
                    }
                    retItemListResponse.add(itemResponse);
                }
                responseEntity = new ResponseEntity<>(retItemListResponse, HttpStatus.CREATED);
            }
        }
        finally
        {
            itemData = null;
            itemResponse = null;
            retItemListResponse = null;
        }
        return responseEntity;
    }

    @ApiOperation(value = "Updates Items", notes = "Updates the specified items.")
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "OK"),
        @ApiResponse(code = 400, message = "Bad Request"),
        @ApiResponse(code = 401, message = "Unauthorized"),
        @ApiResponse(code = 404, message = "Not Found"),
        @ApiResponse(code = 409, message = "Conflict"),
        @ApiResponse(code = 503, message = "Service Unavailable"),
    })
    @RequestMapping(value = "items", method = RequestMethod.PUT,
            produces={MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE, MediaType.TEXT_XML_VALUE})
    public ResponseEntity<ItemListResponse> updateItem(@RequestBody ItemList itemList)
    {
        ResponseEntity<ItemListResponse> responseEntity = null;
        ItemListResponse retItemListResponse = null;
        ItemResponse itemResponse = null;
        ItemData itemData = null;

        try
        {
            if (itemList == null)
            {
                responseEntity = new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            }
            else if (itemList.isEmpty())
            {
                responseEntity = new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            }
            else
            {
                retItemListResponse = new ItemListResponse();

                for (Item item : itemList.getItemList())
                {
                    itemResponse = new ItemResponse();

                    itemData = new ItemData(item.getUpc(), item.getDescription(), item.getBalance());

                    if (itemService.updateItem(itemData) == true)
                    {
                        itemData = itemService.getItem(itemData.getUpc());

                        itemResponse.setUpc(itemData.getUpc());
                        itemResponse.setDescription(itemData.getDescription());
                        itemResponse.setBalance(itemData.getBalance());
                        itemResponse.setStatusCode(HttpStatus.OK.value());
                        itemResponse.setStatus(HttpStatus.OK.getReasonPhrase());
                    }
                    else
                    {
                        itemResponse.setUpc(item.getUpc());
                        itemResponse.setDescription(item.getDescription());
                        itemResponse.setBalance(item.getBalance());
                        itemResponse.setStatusCode(HttpStatus.NOT_FOUND.value());
                        itemResponse.setStatus(HttpStatus.NOT_FOUND.getReasonPhrase());
                    }
                    retItemListResponse.add(itemResponse);
                }
                responseEntity = new ResponseEntity<>(retItemListResponse, HttpStatus.OK);
            }
        }
        finally
        {
            itemData = null;
            itemResponse = null;
            retItemListResponse = null;
        }
        return responseEntity;
    }
}
