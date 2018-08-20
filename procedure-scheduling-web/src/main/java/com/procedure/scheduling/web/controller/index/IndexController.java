package com.procedure.scheduling.web.controller.index;

import com.procedure.scheduling.web.Navigation;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class IndexController {

	@RequestMapping(value = {Navigation.ROOT, Navigation.INDEX})
	public String index() {

		return Navigation.INDEX_HTML;
	}
}
