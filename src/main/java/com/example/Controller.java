/*
 * Copyright 2018-2019 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.example;

import java.util.HashMap;
import java.util.Map;

import com.common.Book;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageHeaders;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class Controller {

	@Autowired
	private KafkaTemplate<Object, Object> template;

	@PostMapping(path = "/send/book/{what}")
	public void buyBook(@PathVariable String what) {
		this.template.send("topic1", new Book(what));
	}

	@PostMapping(path = "/send/book/message/{what}")
	public void buyBookAsMessage(@PathVariable String what) {
		Map<String, Object> headers = new HashMap<>();
		headers.put("kafka_topic", "topic1");
		headers.put("kafka_messageKey", "key-" + "example");
		Message<Book> message = MessageBuilder.createMessage(new Book(what), new MessageHeaders(headers));
		this.template.send(message);
	}

}
