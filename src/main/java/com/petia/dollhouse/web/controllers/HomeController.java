package com.petia.dollhouse.web.controllers;

import com.petia.dollhouse.domain.view.PromoOfferViewModel;
import com.petia.dollhouse.service.CompanyService;
import com.petia.dollhouse.service.PromoOfferService;
import com.petia.dollhouse.service.UserService;
import com.petia.dollhouse.web.annotations.PageTitle;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

import com.petia.dollhouse.constants.Constants;

import java.util.List;

@Controller
public class HomeController extends BaseController {


    private final PromoOfferService promoOfferService;


    @Autowired
    public HomeController(PromoOfferService promoOfferService) {

        this.promoOfferService = promoOfferService;

    }

    @GetMapping(Constants.INDEX_ACTION)
    @PageTitle(Constants.INDEX_TITLE)
    public ModelAndView index(ModelAndView modelAndView) {
        List<PromoOfferViewModel> promoOffers = this.promoOfferService.mapPromoOfferServiceToView(this.promoOfferService.allPromoOffer());
        modelAndView.addObject("promoOffers", promoOffers);

        return super.view(Constants.INDEX_PAGE, modelAndView);

    }

    @GetMapping(Constants.HOME_ACTION)
    @PreAuthorize("isAuthenticated()")
    @PageTitle(Constants.HOME_TITLE)
    public ModelAndView home(ModelAndView modelAndView) {
        List<PromoOfferViewModel> promoOffers = this.promoOfferService.mapPromoOfferServiceToView(this.promoOfferService.allPromoOffer());
        modelAndView.addObject("promoOffers", promoOffers);

        return super.view(Constants.INDEX_PAGE, modelAndView);
    }

    @GetMapping(Constants.ABOUT_ACTION)
    @PageTitle(Constants.ABOUT_TITLE)
    public ModelAndView about(ModelAndView modelAndView) {
        return super.view(Constants.ABOUT_PAGE, modelAndView);

    }

    @GetMapping(Constants.CONTACT_ACTION)
    @PageTitle(Constants.CONTACT_TITLE)
    public ModelAndView contact(ModelAndView modelAndView) {

        return super.view(Constants.CONTACT_PAGE, modelAndView);

    }

}
