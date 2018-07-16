/*
MIT License

Copyright (c) 2017 Universidad de los Andes - ISIS2603

Permission is hereby granted, free of charge, to any person obtaining a copy
of this software and associated documentation files (the "Software"), to deal
in the Software without restriction, including without limitation the rights
to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
copies of the Software, and to permit persons to whom the Software is
furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in all
copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
SOFTWARE.
 */
package co.edu.uniandes.csw.bookstore.tests.postman;

import co.edu.uniandes.csw.bookstore.mappers.BusinessLogicExceptionMapper;
import co.edu.uniandes.csw.bookstore.resources.EditorialResource;
import co.edu.uniandes.csw.postman.tests.PostmanTestBuilder;
import java.io.File;
import java.io.IOException;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.container.test.api.RunAsClient;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.jboss.shrinkwrap.resolver.api.maven.Maven;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 *
 * @author ISIS2603
 */
@RunWith(Arquillian.class)
public class BookStoreIT {

    @Deployment(testable = true)
    public static WebArchive createDeployment() {
        return ShrinkWrap.create(WebArchive.class, "frontstepbystep-web.war")
                // Se agrega las dependencias
                .addAsLibraries(Maven.resolver().loadPomFromFile("pom.xml")
                        .importRuntimeDependencies().resolve()
                        .withTransitivity().asFile())
                // Se agregan los compilados de los paquetes de servicios
                .addPackage(EditorialResource.class.getPackage())
                .addPackage(BusinessLogicExceptionMapper.class.getPackage())
                // El archivo que contiene la configuracion a la base de datos.
                .addAsResource("META-INF/persistence.xml", "META-INF/persistence.xml")
                // El archivo beans.xml es necesario para injeccion de dependencias.
                .addAsWebInfResource(new File("src/main/webapp/WEB-INF/beans.xml"))
                // El archivo web.xml es necesario para el despliegue de los servlets
                .setWebXML(new File("src/main/webapp/WEB-INF/web.xml"));

    }

    @Test
    @RunAsClient
    public void postman() throws IOException {

        PostmanTestBuilder tp = new PostmanTestBuilder();
        tp.setTestWithoutLogin("backstepbystep-paso5.postman_collection");
        String desiredResult = "0";
        if (tp.getAssertions_failed() != null) {
            Assert.assertEquals(desiredResult, tp.getAssertions_failed());
            }
        if (tp.getIterations_failed() != null) {
            Assert.assertEquals(desiredResult, tp.getIterations_failed());
        }
        if (tp.getPrerequest_scripts_failed() != null) {
            Assert.assertEquals(desiredResult, tp.getPrerequest_scripts_failed());
    }
        if (tp.getRequests_failed() != null) {
            Assert.assertEquals(desiredResult, tp.getRequests_failed());
        }
        if (tp.getTest_scripts_failed() != null) {
            Assert.assertEquals(desiredResult, tp.getTest_scripts_failed());
        }

}

}
