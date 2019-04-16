package com.petia.dollhouse.service;

import com.petia.dollhouse.domain.service.PromoOfferServiceModel;
import com.petia.dollhouse.domain.view.PromoOfferViewModel;

import java.util.List;

public interface PromoOfferService {

    List<PromoOfferServiceModel> allPromoOffer();

    List<PromoOfferViewModel> mapPromoOfferServiceToView(List<PromoOfferServiceModel> promoOfferServiceModels);
}
