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
package net.revelc.code.formatter.groovy;

import java.io.IOException;
import java.util.Map;

import org.codehaus.groovy.eclipse.refactoring.formatter.DefaultGroovyFormatter;
import org.codehaus.groovy.eclipse.refactoring.formatter.FormatterPreferencesOnStore;
import org.eclipse.jface.text.BadLocationException;
import org.eclipse.jface.text.Document;
import org.eclipse.jface.text.IDocument;
import org.eclipse.jface.text.TextSelection;
import org.eclipse.text.edits.TextEdit;

import net.revelc.code.formatter.AbstractCacheableFormatter;
import net.revelc.code.formatter.ConfigurationSource;
import net.revelc.code.formatter.Formatter;
import net.revelc.code.formatter.LineEnding;

public class GroovyFormatter extends AbstractCacheableFormatter implements Formatter {

    private FormatterPreferencesOnStore store;

    @Override
    public void init(Map<String, String> options, ConfigurationSource cfg) {
        super.initCfg(cfg);

        store = new FormatterPreferencesOnStore(null);
    }

    @Override
    public String doFormat(String code, LineEnding ending) throws IOException, BadLocationException {

        IDocument doc = new Document(code);
        TextSelection sel = new TextSelection(0, code.length());

        DefaultGroovyFormatter formatter = new DefaultGroovyFormatter(sel, doc, store, false);

        TextEdit te;
        try {
            te = formatter.format();
            if (te == null) {
                this.log.debug(
                        "Code cannot be formatted. Possible cause is unmatched source/target/compliance version.");
                return null;
            }
        } catch (IndexOutOfBoundsException e) {
            this.log.debug("Code cannot be formatted for text -->" + code + "<--", e);
            return null;
        }

        te.apply(doc);
        String formattedCode = doc.get();

        if (code.equals(formattedCode)) {
            return null;
        }
        return formattedCode;
    }

    @Override
    public boolean isInitialized() {
        return true;
    }

}
