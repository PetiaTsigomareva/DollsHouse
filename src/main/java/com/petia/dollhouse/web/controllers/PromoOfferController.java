package com.petia.dollhouse.web.controllers;

import com.petia.dollhouse.constants.Constants;
import com.petia.dollhouse.service.PromoOfferService;
import com.petia.dollhouse.web.annotations.PageTitle;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class PromoOfferController extends BaseController {

    private final ModelMapper modelMapper;
    private final PromoOfferService promoOfferService;

    @Autowired
    public PromoOfferController(ModelMapper modelMapper, PromoOfferService promoOfferService) {
        this.modelMapper = modelMapper;
        this.promoOfferService = promoOfferService;
    }




    @GetMapping(Constants.PROMO_OFFER_ACTION)
    @PageTitle(Constants.PROMO_OFFERS_TITLE)
    public ModelAndView promoOffers(ModelAndView modelAndView) {

        return super.view(Constants.INDEX_PAGE, modelAndView);
    }

//    @GetMapping("/top-offers/{category}")
//    @ResponseBody
//    public List<OfferViewModel> fetchByCategory(@PathVariable String category) {
//        List<OfferViewModel> offerViewModels = this.offerService.findAllOffers()
//                .stream()
//                .map(o -> this.modelMapper.map(o, OfferViewModel.class))
//                .collect(Collectors.toList());
//
//        return offerViewModels;
//    }
}
