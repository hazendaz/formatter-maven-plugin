/*
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package net.revelc.code.formatter.javascript;

import java.util.Map;

import net.revelc.code.formatter.AbstractCacheableFormatter;
import net.revelc.code.formatter.ConfigurationSource;
import net.revelc.code.formatter.Formatter;
import net.revelc.code.formatter.LineEnding;

import org.eclipse.jface.text.BadLocationException;
import org.eclipse.jface.text.Document;
import org.eclipse.jface.text.IDocument;
import org.eclipse.wst.jsdt.core.ToolFactory;
import org.eclipse.wst.jsdt.core.formatter.CodeFormatter;

/**
 * The Class JavascriptFormatter.
 */
public class JavascriptFormatter extends AbstractCacheableFormatter implements Formatter {

    /** The formatter. */
    private CodeFormatter formatter;

    /** The configuration options */
    private Map<String, String> options;

    @Override
    public void init(final Map<String, String> options, final ConfigurationSource cfg) {
        super.initCfg(cfg);

        this.formatter = ToolFactory.createCodeFormatter(options, ToolFactory.M_FORMAT_EXISTING);
        this.options = options;
    }

    @Override
    public String doFormat(final String code, final LineEnding ending) throws BadLocationException {
        final var te = this.formatter.format(CodeFormatter.K_JAVASCRIPT_UNIT, code, 0, code.length(), 0,
                ending.getChars());
        if (te == null) {
            this.log.debug(
                    "Code cannot be formatted. Possible cause " + "is unmatched source/target/compliance version.");
            return null;
        }

        final IDocument doc = new Document(code);
        te.apply(doc);
        final var formattedCode = doc.get();

        if (code.equals(formattedCode)) {
            return null;
        }
        return formattedCode;
    }

    @Override
    public boolean isInitialized() {
        return this.formatter != null;
    }

    /**
     * Gets the options.
     *
     * @return the options
     */
    public Map<String, String> getOptions() {
        return options;
    }

}
