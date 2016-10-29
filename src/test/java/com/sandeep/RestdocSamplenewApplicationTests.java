package com.sandeep;

import static org.springframework.restdocs.hypermedia.HypermediaDocumentation.halLinks;
import static org.springframework.restdocs.hypermedia.HypermediaDocumentation.linkWithRel;
import static org.springframework.restdocs.hypermedia.HypermediaDocumentation.links;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.documentationConfiguration;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.get;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.preprocessResponse;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.prettyPrint;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.restdocs.JUnitRestDocumentation;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment=WebEnvironment.RANDOM_PORT)
public class RestdocSamplenewApplicationTests {

	@Rule
    public final JUnitRestDocumentation restDocumentation = 
    new JUnitRestDocumentation("target/generated-snippets");

	@Autowired
	private WebApplicationContext context;

	private MockMvc mockMvc;

	@Before
	public void setUp() {
	    this.mockMvc = MockMvcBuilders.webAppContextSetup(this.context)
	            .apply(documentationConfiguration(this.restDocumentation)
	            		.uris().withScheme("https").
	            		withHost("rest-docssandeep.apps-np.homedepot.com").
	            		withPort(443))
	            .build();
	}
	
	@Test
	public void shouldGetCar() throws Exception {
		this.mockMvc.perform(get("/cars/1")) 
	    		.andExpect(status().isOk())
	    		.andDo(document("car-get", preprocessResponse(prettyPrint()), 
	    				links(
	    					halLinks(), linkWithRel("self").description("This car")),
	    				responseFields(
	    					fieldWithPath("brand").description("The brand of this car"),
	    					fieldWithPath("_links").description("<<resources-car-links,Links>> to other resources"))));
	}

}
