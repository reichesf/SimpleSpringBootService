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
    public ResponseEntity<ItemData> getItem(@PathVariable String upc)
    {
        ItemData itemData = itemService.getItem(upc);

        if (itemData != null)
        {
            return new ResponseEntity<>(itemData, HttpStatus.OK);
        }
        else
        {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
    }

    @RequestMapping(value = "/item", method = RequestMethod.GET, produces={MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE, MediaType.TEXT_XML_VALUE})
    public ResponseEntity<ItemStorage> getItemList()
    {
        return new ResponseEntity<>(itemService.getItemList(), HttpStatus.OK);
    }

    @RequestMapping(value = "/item", method = RequestMethod.POST, produces={MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE, MediaType.TEXT_XML_VALUE})
    public ResponseEntity<ItemData> addItem(@RequestBody ItemData itemData)
    {
        itemService.addItem(itemData);

        return new ResponseEntity<>(itemData, HttpStatus.OK);
    }
}