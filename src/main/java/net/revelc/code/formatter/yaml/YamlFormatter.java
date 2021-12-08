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
package net.revelc.code.formatter.yaml;

import java.io.IOException;
import java.io.StringReader;
import java.util.Map;

import org.yaml.snakeyaml.DumperOptions;
import org.yaml.snakeyaml.Yaml;
import org.yaml.snakeyaml.DumperOptions.LineBreak;

import net.revelc.code.formatter.AbstractCacheableFormatter;
import net.revelc.code.formatter.ConfigurationSource;
import net.revelc.code.formatter.Formatter;
import net.revelc.code.formatter.LineEnding;

public class YamlFormatter extends AbstractCacheableFormatter implements Formatter {

    private Yaml formatter;

    @Override
    public void init(Map<String, String> options, ConfigurationSource cfg) {
        super.initCfg(cfg);

        int indent = Integer.parseInt(options.getOrDefault("indent", "4"));
        boolean rgbAsHex = Boolean.parseBoolean(options.getOrDefault("rgbAsHex", Boolean.TRUE.toString()));
        boolean useSourceStringValues = Boolean
                .parseBoolean(options.getOrDefault("useSourceStringValues", Boolean.FALSE.toString()));

        DumperOptions dumperOptions = new DumperOptions();
        dumperOptions.setIndent(indent);
        dumperOptions.setPrettyFlow(true);
        dumperOptions.setLineBreak(LineBreak.WIN);

        formatter = new Yaml(dumperOptions);
    }

    @Override
    protected String doFormat(String code, LineEnding ending) throws IOException {

        String formattedCode = formatter.load(code.replaceAll("\\s+", ""));

        if (code.equals(formattedCode)) {
            return null;
        }

        return formattedCode;
    }

    @Override
    public boolean isInitialized() {
        return formatter != null;
    }

}
