/*
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package net.revelc.code.formatter.xml;

import static java.util.Collections.emptyMap;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import net.revelc.code.formatter.AbstractFormatterTest;
import net.revelc.code.formatter.LineEnding;

/**
 * @author yoshiman
 * @author jam01
 */
class XMLFormatterTest extends AbstractFormatterTest {

    @Test
    void testDoFormatFile() throws Exception {
        // Since we set the line endings via options for xml, we cannot rely on CRLF inside twoPassTest.
        // The option will not be available inside xml formatter init so it will use whatever the system
        // default is regardless of requesting it to be CRLF later which is ignored.
        String expectedHash = LineEnding.LF.isSystem()
                ? "a5bfe48d45504b624d5f610bcbb935b117c8190de7c27957a8ba3658df6f3879682c485d77378443399a5d092899105988386c56b14308ac21faf02a82bfdffb"
                : "1fc08d47972da8debc97ef4071bc1a67a7df588513242e0af8a5df507c469a5921e52f096a436713c2291107ca20e1c215069abcd9dc04bed9ccbcb0418932e0";
        LineEnding lineEnding = LineEnding.LF.isSystem() ? LineEnding.LF : LineEnding.CRLF;
        twoPassTest(emptyMap(), new XMLFormatter(), "someFile.xml", expectedHash, lineEnding);
    }

    @Test
    void testIsIntialized() throws Exception {
        XMLFormatter xmlFormatter = new XMLFormatter();
        assertFalse(xmlFormatter.isInitialized());
        xmlFormatter.init(emptyMap(), new AbstractFormatterTest.TestConfigurationSource(TEST_OUTPUT_PRIMARY_DIR));
        assertTrue(xmlFormatter.isInitialized());
    }

}
