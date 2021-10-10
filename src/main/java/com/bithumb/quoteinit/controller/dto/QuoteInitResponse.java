package com.bithumb.quoteinit.controller.dto;

import com.bithumb.utils.domain.Coin;
import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.util.*;

@Data
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
@NoArgsConstructor
@ToString
@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY)
public class QuoteInitResponse {
    private String symbol;
    private String korean;
    private String closePrice;
    private String chgRate;
    private String chgAmt;
    private String accTradeValue;
    private String unitsTraded;
    private String minPrice;
    private String maxPrice;

    public static QuoteInitResponse of(String symbol,String korean, JSONObject quoteObj) {
        return new QuoteInitResponse(symbol,
                korean,
                quoteObj.get("closing_price").toString(),
                quoteObj.get("fluctate_rate_24H").toString(),
                quoteObj.get("fluctate_24H").toString(),
                quoteObj.get("acc_trade_value_24H").toString(),
                quoteObj.get("units_traded_24H").toString(),
                quoteObj.get("min_price").toString(),
                quoteObj.get("max_price").toString()
                );
    }

    public static List<QuoteInitResponse> ofIterater(Iterator keyIterator, Iterator valuesIterator, HashMap<String, Coin> coins) throws ParseException {
        JSONParser jsonParser = new JSONParser();
        List<QuoteInitResponse> quotes = new ArrayList<>();
        while(keyIterator.hasNext()) {
            String symbol = keyIterator.next().toString();
            String values = valuesIterator.next().toString();

            if (symbol.equals("date")) {
                symbol = keyIterator.next().toString();
                values = valuesIterator.next().toString();
            }

            JSONObject quoteObj = (JSONObject)jsonParser.parse(values);
            String korean = coins.get(symbol).getKorean();
            quotes.add(QuoteInitResponse.of(symbol, korean, quoteObj));
        }

        return quotes;
    }
}
