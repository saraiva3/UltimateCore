/*
 * This file is part of UltimateCore, licensed under the MIT License (MIT).
 *
 * Copyright (c) Bammerbom
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */
package bammerbom.ultimatecore.sponge.modules.mail.api;

import bammerbom.ultimatecore.sponge.api.data.Key;
import bammerbom.ultimatecore.sponge.api.data.providers.UserKeyProvider;
import com.google.common.reflect.TypeToken;

import java.util.ArrayList;
import java.util.List;

public class MailKeys {
    public static Key.User<List<Mail>> MAILS_SENT = new Key.User<>("mails_sent", new UserKeyProvider<>("mails_sent", new TypeToken<List<Mail>>() {
    }, new ArrayList<>()));
    public static Key.User<List<Mail>> MAILS_RECEIVED = new Key.User<>("mails_received", new UserKeyProvider<>("mails_received", new TypeToken<List<Mail>>() {
    }, new ArrayList<>()));
    public static Key.User<Integer> UNREAD_MAIL = new Key.User<>("mails_unread", new UserKeyProvider<>("mails_unread", TypeToken.of(Integer.class), 0));
}
