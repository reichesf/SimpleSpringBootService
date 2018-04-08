package com.github.reichesf.itemservice;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public final class Controller
{
    //@Autowired
    private ItemService itemService;

    public Controller(ItemService itemService)
    {
        this.itemService = itemService;
    }

    @RequestMapping("/")
    public String index()
    {
        return "Greetings from ItemData Controller!";
    }

    // Illustrates the following:
    // - Exact specification of the method (GET) on the Request Mapping.
    // - Multiple "produces" on the RequestMapping to handle both JSON and XML.
    // - Use of the ResponseEntity when producing a response.

    @RequestMapping(value = "/item/{upc}", method = RequestMethod.GET, produces={MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE, MediaType.TEXT_XML_VALUE})
    public ResponseEntity getItem(@PathVariable String upc)
    {
        ResponseEntity responseEntity = null;
        ItemData itemData = null;
        ItemList itemList = null;

        try
        {
            if (upc == null)
            {
                responseEntity = new ResponseEntity(itemList, HttpStatus.BAD_REQUEST);
            }
            else
            {
                itemData = itemService.getItem(upc);

                if (itemData != null)
                {
                    itemList = new ItemList();
                    itemList.add(new Item(itemData));

                    responseEntity = new ResponseEntity(itemList, HttpStatus.OK);
                }
                else
                {
                    responseEntity = new ResponseEntity(HttpStatus.NO_CONTENT);
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

    @RequestMapping(value = "/item", method = RequestMethod.GET, produces={MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE, MediaType.TEXT_XML_VALUE})
    public ResponseEntity getItemList()
    {
        ResponseEntity responseEntity = null;
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
                responseEntity = new ResponseEntity(HttpStatus.NO_CONTENT);
            }
            else
            {
                responseEntity = new ResponseEntity(itemList, HttpStatus.OK);
            }
        }
        finally
        {
            itemData = null;
        }
        return responseEntity;
    }

    @RequestMapping(value = "/item", method = RequestMethod.POST, produces={MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE, MediaType.TEXT_XML_VALUE})
    public ResponseEntity addItem(@RequestBody Item item)
    {
        ResponseEntity responseEntity = null;
        Item retItem = null;
        ItemData itemData = null;

        try
        {
            if (item == null)
            {
                responseEntity = new ResponseEntity(HttpStatus.BAD_REQUEST);
            }
            else
            {
                itemData = new ItemData();

                itemData.setUpc(item.getUpc());
                itemData.setDescription(item.getDescription());
                itemData.setBalance(item.getBalance());

                if (itemService.addItem(itemData) == true)
                {
                    retItem = new Item(itemService.getItem(itemData.getUpc()));

                    responseEntity = new ResponseEntity(retItem, HttpStatus.OK);
                }
                else
                {
                    responseEntity = new ResponseEntity(HttpStatus.BAD_REQUEST);
                }
            }
        }
        finally
        {
            itemData = null;
            retItem = null;
        }
        return responseEntity;
    }

    @RequestMapping(value = "/item", method = RequestMethod.PUT, produces={MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE, MediaType.TEXT_XML_VALUE})
    public ResponseEntity updateItem(@RequestBody Item item)
    {
        ResponseEntity responseEntity = null;
        Item retItem = null;
        ItemData itemData = null;

        try
        {
            if (item == null)
            {
                responseEntity = new ResponseEntity(HttpStatus.BAD_REQUEST);
            }
            else
            {
                itemData = new ItemData();

                itemData.setUpc(item.getUpc());
                itemData.setDescription(item.getDescription());
                itemData.setBalance(item.getBalance());

                if (itemService.updateItem(itemData) == true)
                {
                    retItem = new Item(itemService.getItem(itemData.getUpc()));

                    responseEntity = new ResponseEntity(retItem, HttpStatus.OK);
                }
                else
                {
                    responseEntity = new ResponseEntity(HttpStatus.BAD_REQUEST);
                }
            }
        }
        finally
        {
            itemData = null;
            retItem = null;
        }
        return responseEntity;
    }
}