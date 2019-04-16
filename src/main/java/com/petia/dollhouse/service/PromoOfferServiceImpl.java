package com.petia.dollhouse.service;

import com.petia.dollhouse.constants.Constants;
import com.petia.dollhouse.domain.entities.DHService;
import com.petia.dollhouse.domain.entities.PromoOffer;
import com.petia.dollhouse.domain.service.PromoOfferServiceModel;
import com.petia.dollhouse.domain.service.ServiceModel;
import com.petia.dollhouse.domain.view.PromoOfferViewModel;
import com.petia.dollhouse.repositories.PromoOfferRepository;
import com.petia.dollhouse.utils.Utils;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

@Service
public class PromoOfferServiceImpl implements PromoOfferService {
    private final PromoOfferRepository promoOfferRepository;
    private final DollHouseService dollHouseService;
    private final ModelMapper modelMapper;
    private final Random random;


    @Autowired
    public PromoOfferServiceImpl(PromoOfferRepository promoOfferRepository, DollHouseService dollHouseService, ModelMapper modelMapper, Random random) {
        this.promoOfferRepository = promoOfferRepository;
        this.dollHouseService = dollHouseService;
        this.modelMapper = modelMapper;
        this.random = random;
    }

    @Override
    public List<PromoOfferServiceModel> allPromoOffer() {
        List<PromoOfferServiceModel> promoOfferServiceModels;
        List<PromoOffer> offers = this.promoOfferRepository.findAll();
        promoOfferServiceModels = offers.stream().map(o -> this.modelMapper.map(o, PromoOfferServiceModel.class)).collect(Collectors.toList());
        return promoOfferServiceModels;
    }

    @Override
    public List<PromoOfferViewModel> mapPromoOfferServiceToView(List<PromoOfferServiceModel> promoOfferServiceModels) {
        List<PromoOfferViewModel> promoOfferViewModelList;
        promoOfferViewModelList = promoOfferServiceModels.stream().map(psm -> this.modelMapper.map(psm, PromoOfferViewModel.class)).collect(Collectors.toList());
        return promoOfferViewModelList;
    }

    @Scheduled(fixedRate = 30000)
    private void generatePromoOffer() {
        this.promoOfferRepository.deleteAll();

        List<ServiceModel> services = this.dollHouseService.findAll();
        List<PromoOffer> promoOffers = new ArrayList<>();

        if (services.isEmpty()) {
            return;
        }
        for (int i = 0; i < 3; i++) {
            DHService dhService = getRandomServices(services);
            PromoOffer promoOffer = mapPromoOffer(dhService);
            //if (promoOffers.stream().filter(po -> po.getId().equals(promoOffer.getId())).count() == 0) {
                promoOffers.add(promoOffer);
           // }

        }
        this.promoOfferRepository.saveAll(promoOffers);
    }

    private DHService getRandomServices(List<ServiceModel> list) {

        DHService dhService = this.modelMapper.map(list.get(random.nextInt(list.size())), DHService.class);


        return dhService;
    }

    private PromoOffer mapPromoOffer(DHService dhService) {

        PromoOffer promoOffer = new PromoOffer();
        promoOffer.setName(dhService.getName());
        promoOffer.setDescription(dhService.getDescription());
        promoOffer.setPrice(dhService.getPrice().multiply(new BigDecimal(0.10)));
        promoOffer.setStartDate(Utils.createRandomDate(Constants.YEAR, Constants.YEAR));
        promoOffer.setEndDate(Utils.createRandomDate(Constants.YEAR, Constants.YEAR));

        return promoOffer;
    }


}
